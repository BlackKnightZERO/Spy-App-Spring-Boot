package com.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
	private Long id;
	private String agent;
	private String title;
	private String gadget1;
	private String gadget2;
}
