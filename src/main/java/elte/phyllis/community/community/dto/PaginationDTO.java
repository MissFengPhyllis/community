package elte.phyllis.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showProvious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer>pages = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        Integer totalPage;
        if(totalCount%size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }

        pages.add(page);
        for(int i = 1 ;i <=3 ;i++){
            if(page - i > 0 ){
                pages.add(page-1,0);
            }
            if(page + i == totalCount){
                pages.add(page+i);
            }
        }

        //如果当前页数是第一页和最后一页就不展示前一页和后一页
        if(page == 1){
            showProvious = false;
        }else {
            showProvious = true;
        }
        if(page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }

        //如果当前这个分页列表包含了第一页，就不用显示显示回到第一页，最后一页同理
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
