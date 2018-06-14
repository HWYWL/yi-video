package com.yi.vo;

/**
 * 视频喜欢人
 * @author YI
 * @date 2018-6-14 11:41:59
 */
public class PublisherVideo {
    
	public UsersVo publisher;
	public boolean userLikeVideo;

	public UsersVo getPublisher() {
		return publisher;
	}

	public void setPublisher(UsersVo publisher) {
		this.publisher = publisher;
	}

	public boolean isUserLikeVideo() {
		return userLikeVideo;
	}

	public void setUserLikeVideo(boolean userLikeVideo) {
		this.userLikeVideo = userLikeVideo;
	}
}