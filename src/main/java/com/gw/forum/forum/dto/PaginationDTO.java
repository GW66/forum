package com.gw.forum.forum.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO {
    private Integer page;
    private Integer pageCount;
    private Boolean showPrevious;
    private Boolean showNext;
    private Boolean showFirstPage;
    private Boolean showEndPage;
    private List<Integer> pages =new ArrayList<>();
    public PaginationDTO(Integer page,Integer totalCount,Integer size){
        this.page=page;
        Integer pageCount;
        Integer minPage;
        Integer maxPage;
//        计算总页数
        if(totalCount%size!=0){
            pageCount=totalCount/size+1;
        }else {
            pageCount=totalCount/size;
        }
        this.pageCount=pageCount;
//        判断是否有后面的页面
        if (page<pageCount){
            showNext=true;
        }else {
            showNext=false;
        }
//        判断是否有前面的页面
        if (page>1){
            showPrevious=true;
        }else {
            showPrevious=false;
        }
//        判断总数是否大于7
        if (pageCount>7){
//        判断是否显示跳转到最后一页
            if (showNext){
                if (page+3<pageCount){
                    showEndPage=true;
                }else {
                    showEndPage=false;
                }
            }else {
                showEndPage=false;
            }
//        判断是否显示跳转到第一页
            if (showPrevious){
                if (page>4){
                    showFirstPage=true;
                }else {
                    showFirstPage=false;
                }
            }else {
                showFirstPage=false;
            }
//        计算最后显示页码的最小页码数,计算最后显示页码的最大页码数
            if (showFirstPage&&showEndPage){
                minPage=page-3;
                maxPage=page+3;
            }else if (showFirstPage==false&&showEndPage){
                minPage=1;
                maxPage=7;
            }else{
                minPage=pageCount-6;
                maxPage=pageCount;
            }
        }else {
            showFirstPage=false;
            showEndPage=false;
            minPage=1;
            maxPage=pageCount;
        }
        for(Integer i=minPage;i<=maxPage;i++){
            pages.add(i);
        }
    }

    public Integer getPage() {
        return page;
    }

    public Boolean getShowPrevious() {
        return showPrevious;
    }

    public Boolean getShowNext() {
        return showNext;
    }

    public Boolean getShowFirstPage() {
        return showFirstPage;
    }

    public Boolean getShowEndPage() {
        return showEndPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public Integer getPageCount() { return pageCount; }
}
