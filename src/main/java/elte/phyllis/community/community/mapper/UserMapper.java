package elte.phyllis.community.community.mapper;
import elte.phyllis.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    //这里面的形参需要注意一下，如果是一个类的话，就不需要这个@param,否则是需要加上这个@param，并且加上这个形参的名字
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
