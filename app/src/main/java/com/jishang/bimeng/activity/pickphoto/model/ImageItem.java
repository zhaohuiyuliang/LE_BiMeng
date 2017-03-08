package com.jishang.bimeng.activity.pickphoto.model;

import java.io.Serializable;

/**
 * 鍥剧墖瀵硅薄
 *
 */
public class ImageItem implements Serializable
{
	private static final long serialVersionUID = -7188270558443739436L;
	public String imageId;
	public String thumbnailPath;
	public String sourcePath;
	public boolean isSelected = false;
	
	
	
	
	@Override
	public String toString() {
		return "ImageItem [imageId=" + imageId + ", thumbnailPath="
				+ thumbnailPath + ", sourcePath=" + sourcePath
				+ ", isSelected=" + isSelected + "]";
	}
	
}
