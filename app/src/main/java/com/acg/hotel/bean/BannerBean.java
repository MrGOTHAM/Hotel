package com.acg.hotel.bean;

import java.io.Serializable;

/**
 * banner
 * @author 
 */
public class BannerBean implements Serializable {
    /**
     * 轮播id
     */
    private Integer bannerId;

    /**
     * 轮播标题
     */
    private String bannerTitle;

    /**
     * 轮播图片
     */
    private String bannerPicture;

    /**
     * 轮播链接
     */
    private String bannerLink;

    /**
     * 是否显示
     */
    private Boolean isShow;

    /**
     * 是否有效
     */
    private Boolean isValid;

    private static final long serialVersionUID = 1L;

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerPicture() {
        return bannerPicture;
    }

    public void setBannerPicture(String bannerPicture) {
        this.bannerPicture = bannerPicture;
    }

    public String getBannerLink() {
        return bannerLink;
    }

    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BannerBean other = (BannerBean) that;
        return (this.getBannerId() == null ? other.getBannerId() == null : this.getBannerId().equals(other.getBannerId()))
            && (this.getBannerTitle() == null ? other.getBannerTitle() == null : this.getBannerTitle().equals(other.getBannerTitle()))
            && (this.getBannerPicture() == null ? other.getBannerPicture() == null : this.getBannerPicture().equals(other.getBannerPicture()))
            && (this.getBannerLink() == null ? other.getBannerLink() == null : this.getBannerLink().equals(other.getBannerLink()))
            && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()))
            && (this.getIsValid() == null ? other.getIsValid() == null : this.getIsValid().equals(other.getIsValid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBannerId() == null) ? 0 : getBannerId().hashCode());
        result = prime * result + ((getBannerTitle() == null) ? 0 : getBannerTitle().hashCode());
        result = prime * result + ((getBannerPicture() == null) ? 0 : getBannerPicture().hashCode());
        result = prime * result + ((getBannerLink() == null) ? 0 : getBannerLink().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        result = prime * result + ((getIsValid() == null) ? 0 : getIsValid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bannerId=").append(bannerId);
        sb.append(", bannerTitle=").append(bannerTitle);
        sb.append(", bannerPicture=").append(bannerPicture);
        sb.append(", bannerLink=").append(bannerLink);
        sb.append(", isShow=").append(isShow);
        sb.append(", isValid=").append(isValid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}