
package com.jishang.bimeng.utils.db;

import java.util.List;

/** 
 * @author Xing,Ming
 * @version 2016年6月3日 下午6:34:51
 */
public interface SQLOperate {
	
	 public void insert(PersonEntity p);
	 public void delete(String id);
	 public void updatedata(PersonEntity p);
	 public List<PersonEntity> find();
	 public PersonEntity findById(String id);

}
