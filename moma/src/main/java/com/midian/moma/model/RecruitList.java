package com.midian.moma.model;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 首页的实体
 * 
 * @author chu
 *
 */
public class RecruitList extends NetResult {
	private Content content;

	public static RecruitList parse(String json) throws AppException {
		RecruitList res = new RecruitList();
		try {
			res = gson.fromJson(json, RecruitList.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public static class Content {
		private ArrayList<Banner> banners;
		private ArrayList<Recruit> recruits;
		private String total_count;

		public ArrayList<Banner> getBanners() {
			return banners;
		}

		public void setBanners(ArrayList<Banner> banners) {
			this.banners = banners;
		}

		public ArrayList<Recruit> getRecruits() {
			return recruits;
		}

		public void setRecruits(ArrayList<Recruit> recruits) {
			this.recruits = recruits;
		}

		public String getTotal_count() {
			return total_count;
		}

		public void setTotal_count(String total_count) {
			this.total_count = total_count;
		}
	}

	public static class Banner extends NetResult {
		private String banner_id;// banner的id
		private String pic;// bannerprivate String 图片名称
		private String pic_suffix;// 图片后缀
		private String url;// banner的图片private String

		public String getBanner_id() {
			return banner_id;
		}

		public void setBanner_id(String banner_id) {
			this.banner_id = banner_id;
		}

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}

		public String getPic_suffix() {
			return pic_suffix;
		}

		public void setPic_suffix(String pic_suffix) {
			this.pic_suffix = pic_suffix;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

	public static class Recruit extends NetResult {
		private String recruit_id;// 招聘信息id
		private String company_name;// 公司名称
		private String company_thumb_pic;// 公司介绍缩略图名称
		private String company_thumb_pic_suffix;// 公司介绍缩略图后缀
		private String position_name;// 工种名称
		private String welfares;// 福利集合
		private String distance;// 距离
		private String salary_desc;
		private String publish_time;// 发布时间private String

		private ArrayList<Banner> banners;

		public Recruit() {
		}

		public Recruit(int itemViewType) {
			this.itemViewType = itemViewType;
		}

		public String getSalary_desc() {
			return salary_desc;
		}

		public void setSalary_desc(String salary_desc) {
			this.salary_desc = salary_desc;
		}

		public void setBanners(ArrayList<Banner> banners) {
			this.banners = banners;
		}

		public ArrayList<Banner> getBanners() {
			return banners;
		}

		public String getRecruit_id() {
			return recruit_id;
		}

		public void setRecruit_id(String recruit_id) {
			this.recruit_id = recruit_id;
		}

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public String getCompany_thumb_pic() {
			return company_thumb_pic;
		}

		public void setCompany_thumb_pic(String company_thumb_pic) {
			this.company_thumb_pic = company_thumb_pic;
		}

		public String getCompany_thumb_pic_suffix() {
			return company_thumb_pic_suffix;
		}

		public void setCompany_thumb_pic_suffix(String company_thumb_pic_suffix) {
			this.company_thumb_pic_suffix = company_thumb_pic_suffix;
		}

		public String getPosition_name() {
			return position_name;
		}

		public void setPosition_name(String position_name) {
			this.position_name = position_name;
		}

		public String getWelfares() {
			return welfares;
		}

		public void setWelfares(String welfares) {
			this.welfares = welfares;
		}

		public String getDistance() {
			return distance;
		}

		public void setDistance(String distance) {
			this.distance = distance;
		}

		public String getPublish_time() {
			return publish_time;
		}

		public void setPublish_time(String publish_time) {
			this.publish_time = publish_time;
		}

	}

}
