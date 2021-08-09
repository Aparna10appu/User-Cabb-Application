package com.cabapplication.user.entity;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is to map the Json response obtained from the sharepoint
 * **/

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class SPResponseEntity {

	@SerializedName("@odata.context")
	private String odatacontext;
	private List<Value> value;

	@Data
//	@NoArgsConstructor
	@AllArgsConstructor
	public class User {
		private String email;
		private String id;
		private String displayName;
	}

	@Data
//	@NoArgsConstructor
	@AllArgsConstructor
	public class CreatedBy {
		private User user;
	}

	@Data
//	@NoArgsConstructor
	@AllArgsConstructor
	public class LastModifiedBy
	{
		private User user;
	}

	@Data
//	@NoArgsConstructor
	@AllArgsConstructor
	public class ParentReference {
		private String siteId;
	}

	@Data
//	@NoArgsConstructor
	@AllArgsConstructor
	public class ContentType {
		private String id;
		private String name;
	}

	@Data
//	@NoArgsConstructor
	@AllArgsConstructor
	public class Fields {
		@SerializedName("@odata.etag")
		private String odataetag;
		
		@SerializedName("Title")
	    private String title;
		
		@SerializedName("EmployeeName")
		private String employeeName;
		
		@SerializedName("PhoneNumber")
		private long phoneNumber;
		
		@SerializedName("Domain")
		private String domain;
		
		@SerializedName("EmployeeId")
		private String employeeId;
	}

	@Data
//	@NoArgsConstructor
	@AllArgsConstructor
	public class Value {
		
		@SerializedName("@odata.etag")
		private String odataetag;
	    private Date createdDateTime;
		private String eTag;
		private String id;
		private Date lastModifiedDateTime;
		private String webUrl;
		private CreatedBy createdBy;
		private LastModifiedBy lastModifiedBy;
		private ParentReference parentReference;
		private ContentType contentType;
		@SerializedName("fields@odata.context")
		private String fieldsodatacontext;
	    private Fields fields;
	}

}
