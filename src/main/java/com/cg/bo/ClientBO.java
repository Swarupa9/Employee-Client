package com.cg.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientBO {
	private long id;
	private String emp_Name;
	private String emp_EmailId;
	private int emp_Age;
	
}
