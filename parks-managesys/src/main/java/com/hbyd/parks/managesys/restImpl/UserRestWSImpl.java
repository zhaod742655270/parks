package com.hbyd.parks.managesys.restImpl;

import com.hbyd.parks.dao.managesys.UserDao;
import com.hbyd.parks.domain.managesys.User;
import com.hbyd.parks.dto.managesys.UserDTO;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

/**基于 Restful 的 WebService
 * 
 * @author ren_xt
 *
 */
/*
	该类中的注解(JAX-RS annotations)由 javax.ws.rs-api-2.0.jar 提供 具体依赖查看：
	%cxf_home%/samples/restful_dispatch/pom.xml 
	
	@Produces：
		返回给客户端的资源类型，可以指定在 class or method 上
		If @Producesannotation is not specified, then the runtime container 
		will assume that any content type can be produced.
 */
@Service//为了测试方便，这里加上注解
@Path("/")
@Produces({"application/xml","application/json"})
public class UserRestWSImpl {
/*
	In JAX-RS terminology, a class which has the JAX-RS annotations defined 
is termed as a Resource class.
	In JAX-RS terminology, the HTTP methods annotations, that is, @GET and 
@POST are called Request Method Designators, while the methods of a resource 
class annotated with a request method designator are termed as Resource methods

在 JAX-RS 术语中，@GET 和 @POST 被称之为"请求方法标识"，被标注的方法被称之为"资源方法"，被标注的类称为 "资源类"

Restful WebService 异常处理方式

	To deal with exceptions, JAX-RS provides the WebApplicationException, which 
	extends the Java RuntimeExceptionclass. The WebApplicationException can 
	take an HTTP status code or javax.ws.rs.core.Responseobject as part of the 
	constructor. The Response object can be used to set the entity information providing 
	a user readable error message along with the HTTP status code.
	
	
	Typically, exception handling for RESTful service would fall into one of the 
	following categories: 
	1. The implementation class can throw an unchecked 
	WebApplicationException with the required HTTP Error code. The 
	HTTP specification defines which HTTP response code should be used for 
	unsuccessful requests, which can be interpreted by clients in a standard way. 
	For example, Status code 4xx defines client error, such as Bad request, and 
	5xx defines the server request where server failed to fulfill a valid request. 
	
	2. The implementation class can create a javax.ws.rs.core.Response object 
	and send a custom error message to the client or send the required HTTP 
	status code in the response.
	
	3. The implementation class can throw a checked exception, and you can 
	wire an Exception Resolver implementation to convert the application 
	exception to the Response object. The ExceptionResolverinterface 
	provides a contract for a provider that maps Java exception to a Response. 
	For instance, if you are fetching information from the database, and a 
	record does not exist, and your application throws a RecordNotFound
	exception, then you can map this exception to a Response object using your 
	ExceptionResolverimplementation. The Response object can be populated 
	with a custom error message and sent back to the client, as mentioned in the 
	second approach.

	1. throw new WebApplicationException(...) extends RuntimeException
		示例：
		throw new WebApplicationException(Response response)
		throw new WebApplicationException(int status)
		throw new WebApplicationException(Status status)
		throw new WebApplicationException(String message)
		throw new WebApplicationException(Throwable cause)
		... 其他构造方法
		
		CXF 会将 WebApplicationException 转换为 HTTP 格式
		
	2. return javax.ws.rs.core.Response()
		示例： 
		return Response.status(Status.BAD_REQUEST).build();
		缺点：需要目标方法的返回值为 Response 类型
		
	3. 不常用
		
*/
	@Resource
	private UserDao userDao;
	
	@Resource
	private DozerBeanMapper dozerMapper; 
	
	@GET
	@Path("/user/{id}")//请求路径可以内嵌变量
	@Produces({"application/xml","application/json"})
	public UserDTO getUser(@PathParam("id") String id){
		System.out.println("UserRestServiceImpl.getUser() called with id: " + id);
		User user = userDao.getById(id);

		if(user == null){//目标用户不存在
//			构造 HTTP 响应的 Builder
			ResponseBuilder builder = Response.status(Status.BAD_REQUEST);//HTTP 状态码，可以在浏览器 F12->Network 查看请求状态
			
			builder.type(MediaType.APPLICATION_XML);//MIME 类型
			builder.entity("<error>User Not Found</error>");//自定义信息，IE 不会显示，但 FF 和 Chrome 会显示
			
			throw new WebApplicationException(builder.build());//send to client
		}
		
		return dozerMapper.map(user, UserDTO.class);
	}
	
	@POST
	@Path("/user")
	@Consumes({"application/xml","application/json"})
	public Response addUser(UserDTO userDTO){
		System.out.println("UserRestServiceImpl.addUser()");
		
//		保存前先判断用户是否存在，用户不重名为判断标准，用户名称为必填信息，不必判断是否为 null 或 ''
		if(userDao.getByName(userDTO.getUserName()) != null){//同名用户存在
			return Response.status(Status.BAD_REQUEST).entity("<error>User with same name already exist</error>").build();
		}else{
			User u = userDao.save(dozerMapper.map(userDTO, User.class));
			userDTO.setId(u.getId());//userDTO 需要添加 id 再返回，方便后续的更新操作
			return Response.ok(userDTO).build();//如果方法需要返回值，可以将其作为 ok() 的参数
		}
		
	}
	
//	The @PathParam annotation is used to map a given URI Path template variable to the method parameter
	@DELETE
	@Path("/user/{id}")
	public Response deleteUser(@PathParam("id") String id){
		System.out.println("UserRestServiceImpl.deleteUser()");
		
		if(userDao.getById(id) == null){//要删除的目标对象不存在
			return Response.status(Status.BAD_REQUEST).entity("<error>User to delete does not exist</error>").build();
		}else{
			userDao.delete(id);
			return Response.ok().build();//方法无需返回值，因此 ok() 无参数
		}
	}
	
	@PUT
	@Path("/user")
	@Consumes({"application/xml","application/json"})
	public Response updateUser(UserDTO user){
		//更新的目标肯定是有 ID 的
		if(userDao.getById(user.getId()) == null){//更新的目标对象不存在
			return Response.status(Status.BAD_REQUEST).entity("<error>User to update does not exist</error>").build();
		}else{
			userDao.update(dozerMapper.map(user, User.class));
			return Response.ok().build();
		}
	}
}
