package com.hbyd.parks.attendancesys.HQLRunner;

import com.hbyd.parks.domain.doorsys.AccessEvent;
import com.hbyd.parks.domain.managesys.Priviledge;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.QueryTranslator;
import org.hibernate.hql.QueryTranslatorFactory;
import org.hibernate.hql.ast.ASTQueryTranslatorFactory;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;


/**
 * https://github.com/maheskrishnan/HQLRunner
 * git clone https://github.com/maheskrishnan/HQLRunner.git
 */
public class HQLRunner extends JFrame implements ActionListener, ListSelectionListener {

    private SessionFactory sessionFactory = null;

    // all containers
    private JPanel pnlTop = new JPanel(new BorderLayout());
    private JPanel pnlBottom = new JPanel(new BorderLayout());
    private JSplitPane pnlSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnlTop, pnlBottom);

    // all components
    private JTextArea txtHQL = new JTextArea(6, 100);

    private JComboBox cmbLimit = new JComboBox(new String[]{"All", "100", "1000", "10000"});
    private JButton btnRunHQL = new JButton("Run HQL Query");
    private JButton btnRunSQL = new JButton("Run SQL Query");

    private JTable tblResult = new JTable();
    private JTextArea txtGeneratedSQL = new JTextArea();
    private JList lstHistory = new JList();

    // model holds all the previously executed queries...
    private List<String> lstQueryHistory = new ArrayList<String>();

    private HQLRunner(SessionFactory sessFact) {

        this.sessionFactory = sessFact;

        this.setMinimumSize(new Dimension(800, 600));
        this.setTitle("HQL Runner");

        this.pnlTop.setBorder(new javax.swing.border.TitledBorder("Enter your HQL below and hit 'Run'"));
        this.pnlTop.add(this.txtHQL, BorderLayout.CENTER);
        this.cmbLimit.setSelectedIndex(1); // limit 100 is selected by default...
        this.btnRunHQL.addActionListener(this);
        this.btnRunSQL.addActionListener(this);

        this.pnlTop.add(new JPanel() {{
            add(new JLabel(" Limit"));
            add(cmbLimit);
            add(new JLabel("Rows "));
            add(btnRunHQL);
            add(btnRunSQL);
        }}
                , BorderLayout.SOUTH);

        this.pnlBottom.add(new JTabbedPane() {{
            addTab("Result", new JScrollPane(tblResult));
            addTab("Generated SQL", new JScrollPane(txtGeneratedSQL));
            addTab("History", lstHistory);
        }}, BorderLayout.CENTER);

        lstHistory.addListSelectionListener(this);
        lstHistory.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        this.pnlSplit.setDividerLocation(0.4d);

        this.getContentPane().add(this.pnlSplit);

        this.setVisible(true);
    }

    public void runHQL(String strHQL) {
        this.addToHistory(strHQL);
        this.txtGeneratedSQL.setText(this.toSql(strHQL));

//        org.hibernate.HibernateException: No Hibernate Session bound to thread,
//        and configuration does not allow creation of non-transactional one here
//        如果 SessionFactory 是从 Spring 中获取的，Spring 会拦截 getCurrentSession(), 要求这个 Session 必须带有事务，否则报异常。
//        如果 SessionFactory 是从 Hibernate 配置直接构建的，不会出现这个问题，因为 Hibernate 不强制要求 Session 带有事务。
        Session session = sessionFactory.getCurrentSession();

//        Session session = sessionFactory.openSession();

        if (!session.getTransaction().isActive())
            session.beginTransaction();
        Query q = session.createQuery(strHQL);
        if (!"All".equals(this.cmbLimit.getSelectedItem()))
            q.setMaxResults(Integer.parseInt((String) this.cmbLimit.getSelectedItem()));
        List lst = q.list();
        this.setResult(lst);
    }

    public void runSQL(String strHQL) {
        this.addToHistory(strHQL);
        if (!sessionFactory.getCurrentSession().getTransaction().isActive())
            sessionFactory.getCurrentSession().beginTransaction();
        Query q = sessionFactory.getCurrentSession().createSQLQuery(strHQL);
        if (!"All".equals(this.cmbLimit.getSelectedItem()))
            q.setMaxResults(Integer.parseInt((String) this.cmbLimit.getSelectedItem()));
        List lst = q.list();
        this.setResult(lst);
    }

    private void addToHistory(String strHQL) {

        lstQueryHistory.add(strHQL);
        lstHistory.setModel(new ListModel() {
            @Override
            public void removeListDataListener(ListDataListener arg0) {
            }

            @Override
            public int getSize() {
                return lstQueryHistory.size();
            }

            @Override
            public Object getElementAt(int index) {
                return lstQueryHistory.get(index);
            }

            @Override
            public void addListDataListener(ListDataListener arg0) {
            }
        });
    }

    private void setResult(final List lst) {


        tblResult.setModel(new TableModel() {

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            }

            @Override
            public void removeTableModelListener(TableModelListener l) {
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object row = lst.get(rowIndex);
                if (row instanceof Object[]) {
                    Object[] cols = (Object[]) row;
                    return cols[columnIndex];
                } else {
                    try {
                        BeanInfo testBeanInfo = Introspector.getBeanInfo(row.getClass());
                        return testBeanInfo.getPropertyDescriptors()[columnIndex].getReadMethod().invoke(row);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return row;
                    }
                }
            }

            @Override
            public int getRowCount() {
                return lst.size();
            }

            @Override
            public String getColumnName(int columnIndex) {
                if (lst.size() > 0) {
                    Object row = lst.get(0);
                    if (row instanceof Object[]) {
                        return ((Object[]) row)[columnIndex].getClass().getName();
                    } else {
                        try {
                            BeanInfo testBeanInfo = Introspector.getBeanInfo(row.getClass());
                            return testBeanInfo.getPropertyDescriptors()[columnIndex].getName();
                        } catch (IntrospectionException e) {
                            e.printStackTrace();
                            return "????";
                        }
                    }
                }
                return "";
            }

            @Override
            public int getColumnCount() {
                if (lst.size() > 0) {
                    Object row = lst.get(0);
                    if (row instanceof Object[]) {
                        return ((Object[]) row).length;
                    } else {
                        try {
                            BeanInfo testBeanInfo = Introspector.getBeanInfo(row.getClass());
                            return testBeanInfo.getPropertyDescriptors().length;
                        } catch (IntrospectionException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }
                }
                return 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (lst.size() > 0) {
                    Object row = lst.get(0);
                    if (row instanceof Object[]) {
                        return ((Object[]) row)[columnIndex].getClass();
                    } else return Object.class;
                }
                return null;
            }

            @Override
            public void addTableModelListener(TableModelListener l) {

            }
        });
    }

    // ref: http://narcanti.keyboardsamurais.de/hibernate-hql-to-sql-translation.html
    public String toSql(String hqlQueryText) {

        if (hqlQueryText != null && hqlQueryText.trim().length() > 0) {
            final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
            final SessionFactoryImplementor factory = (SessionFactoryImplementor) sessionFactory;
            final QueryTranslator translator = translatorFactory.createQueryTranslator(hqlQueryText, hqlQueryText, java.util.Collections.EMPTY_MAP, factory);
            translator.compile(java.util.Collections.EMPTY_MAP, false);
            return translator.getSQLString();
        }
        return null;
    }


    /**
     * *  Event-Handlers  ***
     */
    public void actionPerformed(ActionEvent e) {
        this.setResult(new ArrayList());
        this.txtGeneratedSQL.setText("");
        if (e.getSource() == btnRunHQL) this.runHQL(txtHQL.getText());
        else if (e.getSource() == btnRunSQL) this.runSQL(txtHQL.getText());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        txtHQL.setText(lstQueryHistory.get(lstHistory.getSelectedIndex()));
    }


    /**
     * 启动 HQLRunner
     */
    public static void main(String[] args) {
//        SessionFactory sf = (SessionFactory) new ClassPathXmlApplicationContext("/spring/applicationContext_HQLRunner.xml").getBean("sessionFactory");

//        代码方式构建 SessionFactory
        Configuration cfg = new Configuration()
                //为了测试方便，只添加无关联的注解实体
                .addAnnotatedClass(AccessEvent.class)
                .addAnnotatedClass(Priviledge.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2005Dialect")
                .setProperty("hibernate.connection.driver_class", "net.sourceforge.jtds.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:jtds:sqlserver://164.70.6.167:1433/parks")
                .setProperty("hibernate.connection.username", "sa")
                .setProperty("hibernate.connection.password", "afxt")
                .setProperty("hibernate.current_session_context_class", "thread");

        SessionFactory sf = cfg.buildSessionFactory();
        HQLRunner runner = new HQLRunner(sf);
        runner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
