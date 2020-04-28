package elte.phyllis.community.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;//万一github将来的用户暴增
    private String bio;//用户描述
    private String avatarUrl;//用户头像


}
