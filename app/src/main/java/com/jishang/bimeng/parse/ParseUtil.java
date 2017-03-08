package com.jishang.bimeng.parse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.jishang.bimeng.entity.chat.AddFriendEntity;
import com.jishang.bimeng.entity.chat.UserDetailEntity;
import com.jishang.bimeng.entity.dt.DtEntity;
import com.jishang.bimeng.entity.dt.Dt_pinglunEntity;
import com.jishang.bimeng.entity.login.LogEntity;
import com.jishang.bimeng.entity.regist.BarEntity;
import com.jishang.bimeng.entity.regist.RegistEntity;
import com.jishang.bimeng.entity.regist.Regist_cityEntity;
import com.jishang.bimeng.entity.regist.Regist_provinceEntity;
import com.jishang.bimeng.entity.regist.YzmEntity;
import com.jishang.bimeng.entity.shipin.ShipinEntity;

public class ParseUtil {
	/**
	 * @param s
	 * @return 登陆之后返回的信息
	 */
	/*public static LogEntity getBanner(String s) {
		LogEntity entity = new LogEntity();
		try {
			JSONObject jobt1 = new JSONObject(s);
			// Log.e("message", jobt1.getString("message"));
			entity.setStatus(jobt1.getInt("status"));
			if (jobt1.getInt("status") == 0) {
				entity.setErrors(jobt1.getString("errors"));
			} else {
				entity.setStatus_code(jobt1.getString("status_code"));
			}
			JSONObject jobt2 = jobt1.getJSONObject("data");
			entity.setPhone(jobt2.getString("phone"));
			entity.setUid(jobt2.getString("uid"));
			entity.setAccess_token(jobt2.getString("access_token"));
			entity.setUsername(jobt2.getString("username"));
			entity.setH_username(jobt2.getString("h_username"));
			entity.setH_password(jobt2.getString("h_password"));
			entity.setHead_img(jobt2.getString("head_img"));
			entity.setDescribetion_info(jobt2.getString("describetion_info"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entity;
	}*/

	/**
	 * @param s
	 * @return 注册返回的信息
	 */
	public static RegistEntity getBanner_regist(String s) {
		RegistEntity entity = new RegistEntity();
		try {
			JSONObject jobt1 = new JSONObject(s);
			// Log.e("message", jobt1.getString("message"));
			entity.setStatus(jobt1.getInt("status"));
			if (jobt1.getInt("status") == 0) {
				entity.setErrors(jobt1.getString("errors"));
			} else {
				entity.setStatus_code(jobt1.getString("status_code"));
			}
			JSONObject jobt2 = jobt1.getJSONObject("data");
			/*entity.setData();setPhone(jobt2.getString("phone"));
			entity.setUid(jobt2.getString("uid"));*/
			if (jobt2.getString("uid") != null) {
				Log.e("uid", jobt2.getString("uid"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * @param s
	 * @return 获取城市信息
	 */
	public static List<Regist_provinceEntity> getBanner_province(String s) {
		List<Regist_provinceEntity> entities = new ArrayList<Regist_provinceEntity>();

		try {
			JSONObject jobt1 = new JSONObject(s);
			JSONArray jaArray1 = jobt1.getJSONArray("province");

			for (int i = 0; i < jaArray1.length(); i++) {
				Regist_provinceEntity entity = new Regist_provinceEntity();
				JSONObject jobt2 = jaArray1.getJSONObject(i);
				entity.setS_provname(jobt2.getString("s_provname"));
				entity.setId(jobt2.getString("id"));
				entities.add(entity);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entities;
	}

	/**
	 * @param s
	 * @return 获取市区
	 */
	public static List<Regist_cityEntity> getBanner_city(String s) {
		List<Regist_cityEntity> entities = new ArrayList<Regist_cityEntity>();

		try {
			JSONObject jobt1 = new JSONObject(s);
			JSONArray jaArray1 = jobt1.getJSONArray("city");
			for (int i = 0; i < jaArray1.length(); i++) {
				Regist_cityEntity entity = new Regist_cityEntity();
				JSONObject jobt2 = jaArray1.getJSONObject(i);
				entity.setS_cityname(jobt2.getString("s_cityname"));
				entity.setId(jobt2.getString("id"));
				entity.setN_provid(jobt2.getString("n_provid"));

				entities.add(entity);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entities;
	}

	/**
	 * @param s
	 * @return 获取省市
	 */
	/*
	 * public static Map<String, String[]> getBanner_city1(String s) {
	 * Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	 * String[] mProvinceDatas; String[] mCitiesDatas; try { JSONObject jobt1 =
	 * new JSONObject(s); JSONArray jaArray1 = jobt1.getJSONArray("province");
	 * mProvinceDatas = new String[jaArray1.length()]; JSONArray jaArray2 =
	 * jobt1.getJSONArray("city"); mCitiesDatas = new String[jaArray2.length()];
	 * for (int i = 0; i < jaArray2.length(); i++) { JSONObject jobt2 =
	 * jaArray1.getJSONObject(i); String province =
	 * jobt2.getString("s_provname"); mProvinceDatas[i] = province;
	 * 
	 * JSONObject jobt3 = jaArray2.getJSONObject(i); String city =
	 * jobt3.getString("s_cityname"); Log.e("city", city); mCitiesDatas[i] =
	 * city;
	 * 
	 * mCitisDatasMap.put(province, mCitiesDatas); }
	 * 
	 * 
	 * 
	 * 
	 * } catch (JSONException e) { e.printStackTrace(); }
	 * Log.e("mCitisDatasMap", mCitisDatasMap.toString()); return
	 * mCitisDatasMap; }
	 */
	/**
	 * @param s
	 * @return 请求之后返回的信息
	 */
	public static YzmEntity getBanner_yanzhengma(String s) {
		YzmEntity entity = new YzmEntity();
		try {
			JSONObject jobt1 = new JSONObject(s);
			// Log.e("message", jobt1.getString("message"));
			entity.setStatus(jobt1.getInt("status"));
			if (jobt1.getInt("status") == 0) {
				entity.setErrors(jobt1.getString("errors"));
			} else {
				entity.setStatus_code(jobt1.getString("status_code"));
			}
			JSONObject jobt2 = jobt1.getJSONObject("data");
			entity.setVerify_code(jobt2.getString("verify_code"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * @param s
	 * @return 获取图片
	 */
	public static List<BarEntity> getBanner_photo(String s) {
		List<BarEntity> entities = new ArrayList<BarEntity>();

		try {
			JSONObject jobt1 = new JSONObject(s);
			JSONArray jaArray1 = jobt1.getJSONArray("business");
			for (int i = 0; i < jaArray1.length(); i++) {
				BarEntity entity = new BarEntity();
				JSONObject jobt2 = jaArray1.getJSONObject(i);
				entity.setId(jobt2.getString("id"));
				entity.setW_img(jobt2.getString("w_img"));
				entity.setW_name(jobt2.getString("w_name"));

				entities.add(entity);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entities;
	}

	/**
	 * @param s
	 * @return 获取视频
	 *//*
	public static List<ShipinEntity> getBanner_shipin(String s) {
		List<ShipinEntity> entities = new ArrayList<ShipinEntity>();

		try {
			JSONObject jobt1 = new JSONObject(s);
			JSONArray jaArray1 = jobt1.getJSONArray("data");
			for (int i = 0; i < jaArray1.length(); i++) {
				ShipinEntity entity = new ShipinEntity();
				JSONObject jobt2 = jaArray1.getJSONObject(i);
				entity.setTitle(jobt2.getString("title"));
				entity.setVid_https(jobt2.getString("vid_https"));
				entity.setVid_thumb(jobt2.getString("vid_thumb"));
				entity.setVid_content(jobt2.getString("vid_content"));

				entities.add(entity);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entities;
	}*/

	/**
	 * @param s
	 * @return 搜索用户信息
	 *//*
	public static UserDetailEntity getBanner_search(String s) {
		UserDetailEntity entity = new UserDetailEntity();
		try {
			JSONObject jobt1 = new JSONObject(s);
			// Log.e("message", jobt1.getString("message"));
			entity.setStatus(jobt1.getInt("status"));
			if (jobt1.getInt("status") == 0) {
				entity.setErrors(jobt1.getString("errors"));
			} else {
				entity.setStatus_code(jobt1.getString("status_code"));
				JSONObject jobt3=jobt1.getJSONObject("relation");
				entity.setF_status(jobt3.getInt("f_status"));
			}
			JSONArray jaArray1 = jobt1.getJSONArray("data");

			JSONObject jobt2 = jaArray1.getJSONObject(0);
			entity.setId(jobt2.getString("id"));
			entity.setUsername(jobt2.getString("username"));
			entity.setEmail(jobt2.getString("email"));
			entity.setPhone(jobt2.getString("phone"));
			entity.setHead_img(jobt2.getString("head_img"));
			entity.setSex(jobt2.getString("sex"));
			entity.setProvince(jobt2.getString("province"));
			entity.setCity(jobt2.getString("city"));
			entity.setBusiness(jobt2.getString("business"));
			entity.setH_username(jobt2.getString("h_username"));
			entity.setH_password(jobt2.getString("h_password"));

			entity.setUid(jobt2.getString("uid"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entity;
	}*/

	/**
	 * @param s
	 * @return 获取动态
	 */
	public static List<DtEntity> getBanner_dt(String s) {
		List<DtEntity> entities = new ArrayList<DtEntity>();
		try {
			JSONObject jobt1 = new JSONObject(s);
			JSONArray jaArray1 = jobt1.getJSONArray("user_content");
			for (int i = 0; i < jaArray1.length(); i++) {
				DtEntity entity = new DtEntity();
				JSONObject jobt2 = jaArray1.getJSONObject(i);
				entity.setContent(jobt2.getString("content"));
				entity.setCreated_time(jobt2.getString("created_time"));
				entity.setU_id(jobt2.getString("u_id"));
				entity.setUc_id(jobt2.getString("uc_id"));
				entity.setThumb_img(jobt2.getString("thumb_img"));
				JSONObject jobt3 = jobt2.getJSONObject("user_post");
				// Log.e("----", jobt3.getString("username"));
				entity.setHeadimg(jobt3.getString("head_img"));
				entity.setUsername(jobt3.getString("username"));
				// entity.setId(jobt2.getString("id"));
				entities.add(entity);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entities;
	}

	/**
	 * @param s
	 * @return 获取动态评论数据
	 */
	public static List<Dt_pinglunEntity> getBanner_dt_pinlun(String s) {
		List<Dt_pinglunEntity> entities = new ArrayList<Dt_pinglunEntity>();
		try {
			JSONObject jobt1 = new JSONObject(s);
			JSONArray jaArray1 = jobt1.getJSONArray("user_content");
			for (int i = 0; i < jaArray1.length(); i++) {
				Dt_pinglunEntity entity = new Dt_pinglunEntity();
				JSONObject jobt2 = jaArray1.getJSONObject(i);
				JSONArray jaArray2 = jobt2.getJSONArray("comments");
				JSONObject jobt3 = null;
				for (int j = 0; j < jaArray2.length(); j++) {

					jobt3 = jaArray2.getJSONObject(j);
					// Log.e("----", jobt3.getString("message"));
					entity.setMessage(jobt3.getString("message"));
					JSONObject jobt4=jobt3.getJSONObject("comment_users");
					entity.setUsername(jobt4.getString("username"));

				}
				entities.add(entity);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entities;
	}

	/**
	 * @param s
	 * @return 发布动态返回的信息
	 */
	public static YzmEntity getBanner_fbdt(String s) {
		YzmEntity entity = new YzmEntity();
		try {
			JSONObject jobt1 = new JSONObject(s);
			// Log.e("message", jobt1.getString("message"));
			entity.setStatus(jobt1.getInt("status"));
			if (jobt1.getInt("status") == 0) {
				entity.setErrors(jobt1.getString("errors"));
			} else {
				entity.setStatus_code(jobt1.getString("status_code"));
			}
			JSONObject jobt2 = jobt1.getJSONObject("data");
			// entity.setVerify_code(jobt2.getString("verify_code"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * @param s
	 * @return 评论之后之后返回的信息
	 */
	public static LogEntity getBanner_pinglun(String s) {
		LogEntity entity = new LogEntity();
		try {
			JSONObject jobt1 = new JSONObject(s);
			// Log.e("message", jobt1.getString("message"));
			entity.setStatus(jobt1.getInt("status"));
			if (jobt1.getInt("status") == 0) {
				entity.setErrors(jobt1.getString("errors"));
			} else {
				entity.setStatus_code(jobt1.getString("status_code"));
			}
			JSONObject jobt2 = jobt1.getJSONObject("data");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	/**
	 * @param s
	 * @return 添加好友返回的数据
	 */
	public static AddFriendEntity getBanner_addfd(String s) {
		AddFriendEntity entity = new AddFriendEntity();
		try {
			JSONObject jobt1 = new JSONObject(s);
			// Log.e("message", jobt1.getString("message"));
			entity.setStatus(jobt1.getInt("status"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return entity;
	}

}
