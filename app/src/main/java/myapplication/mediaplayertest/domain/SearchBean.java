package myapplication.mediaplayertest.domain;

import java.util.List;

/**
 * Created by zhouzhou on 2017/5/27.
 */

public class SearchBean {

    private String flag;
    private String pageNo;
    private String pageSize;
    private String wd;
    private String total;
    private List<ItemsBean> items;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * itemID : ARTIstjlpDFqCB6V7e50XcKk170514
         * itemTitle : 【夜读】和为贵
         * itemType : article_flag
         * detailUrl : http://app.cntv.cn/special/news/detail/arti/index.html?id=ARTIstjlpDFqCB6V7e50XcKk170514&isfromapp=1
         * pubTime : 2017-05-14 00:46:37
         * keywords :
         * category : 要闻
         * guid :
         * videoLength :
         * source : 央视新闻客户端
         * brief :
         * photoCount : 0
         * sub_column_id : PAGE1373939356250140
         * datecheck : 2017-05-14
         * itemImage : {"imgUrl1":"http://p1.img.cctvpic.com/cportal/img/photoAlbum/page/performance/img/2017/5/14/1494693991842_736_668x370.jpg"}
         */

        private String itemID;
        private String itemTitle;
        private String itemType;
        private String detailUrl;
        private String pubTime;
        private String keywords;
        private String category;
        private String guid;
        private String videoLength;
        private String source;
        private String brief;
        private String photoCount;
        private String sub_column_id;
        private String datecheck;
        private ItemImageBean itemImage;

        public String getItemID() {
            return itemID;
        }

        public void setItemID(String itemID) {
            this.itemID = itemID;
        }

        public String getItemTitle() {
            return itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getVideoLength() {
            return videoLength;
        }

        public void setVideoLength(String videoLength) {
            this.videoLength = videoLength;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getPhotoCount() {
            return photoCount;
        }

        public void setPhotoCount(String photoCount) {
            this.photoCount = photoCount;
        }

        public String getSub_column_id() {
            return sub_column_id;
        }

        public void setSub_column_id(String sub_column_id) {
            this.sub_column_id = sub_column_id;
        }

        public String getDatecheck() {
            return datecheck;
        }

        public void setDatecheck(String datecheck) {
            this.datecheck = datecheck;
        }

        public ItemImageBean getItemImage() {
            return itemImage;
        }

        public void setItemImage(ItemImageBean itemImage) {
            this.itemImage = itemImage;
        }

        public static class ItemImageBean {
            /**
             * imgUrl1 : http://p1.img.cctvpic.com/cportal/img/photoAlbum/page/performance/img/2017/5/14/1494693991842_736_668x370.jpg
             */

            private String imgUrl1;

            public String getImgUrl1() {
                return imgUrl1;
            }

            public void setImgUrl1(String imgUrl1) {
                this.imgUrl1 = imgUrl1;
            }
        }
    }
}
