package com.cos.mixin.domain.school.data;

import java.util.List;

import lombok.Data;

@Data
public class DataSearch {
	    private List<Content> content;

	    public List<Content> getContent() {
	        return content;
	    }

	    public void setContent(List<Content> content) {
	        this.content = content;
	    }

}
