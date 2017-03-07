/**
 * easyUI - mask
 * easyUI - 遮罩
 * @author isea533
 * @author http://blog.csdn.net/isea533
 * 
 *
 * Requires:
 * 依赖:
 * 		jquery.js
 * 		jquery.easyui.js
 * 
 * How to use:
 * 使用方法:
 * 		$('.class').easyMask('show'[,options]);
 *		$('.class').easyMask('hide'[,options]);
 *
 *		$.easyMask('.class','show'[,options]);
 *		$.easyMask('.class','hide'[,options]);
 * 		
 *		options = {msg:''}
 *		default options = $.easyMask.options;
 */
(function($){
	//$对象
	$.fn.easyMask = function(method,options){
		return $.easyMask(this,method,options);
	}
	//全局函数
	$.easyMask = function(target,method,options){
		var tar = target||'body';
		var $targ = $(tar);
		var opt = $.extend({},$.easyMask.options,options);
		var method = $.easyMask.methods[method];
		if(method){
			return method(tar,opt);
		}
		return $targ;
	};
	
	$.easyMask.methods = {
		show:function(target,options){
			return $(target).each(function(){
				var $targ = $(this);
				//如果当前对象不是relative,那就添加该属性
				//$("#hehe").css("position")
				if($targ.css('position')!='relative'){
					$targ.data('position',$targ.css('position'));
					$targ.css('position','relative');
				}
				$('<div class=\'datagrid-mask\' style=\"display:block\"></div>').appendTo($targ);
				var msg = $('<div class=\'datagrid-mask-msg\' style=\"display:block;left:50%\"></div>')
							.html(options.msg).appendTo($targ);
				msg.css("marginLeft", -msg.outerWidth() / 2);
			});
		},
		hide:function(target,options){
			return $(target).each(function(){
				$here = $(this);
				$here.children('.datagrid-mask').remove();  
				$here.children('.datagrid-mask-msg').remove();
				//还原position属性
				if($here.data().position!=undefined){
					$here.css("position",$here.data().position);
					$here.removeData('position');
				}
			});
		}
	}
	
	$.easyMask.options = {
		msg:'Loading...'
	};
})(jQuery)