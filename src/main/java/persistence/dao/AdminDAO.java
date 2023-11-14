package persistence.dao;

import org.apache.ibatis.session.SqlSessionFactory;

public class AdminDAO extends UserDAO
{
    public AdminDAO(SqlSessionFactory sqlSessionFactory)
    {
        super(sqlSessionFactory);
    }
    
    public void approval()
    {
    
    }
}
