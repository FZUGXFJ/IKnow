package org.gxfj.iknow.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qmbx
 * 5月2日
 */
public class ViewCommentsJSON {
    Integer resultCode;
    List<CommentJSON> comments = new ArrayList<>();

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public List<CommentJSON> getComments() {
        return comments;
    }

    public void setComments(List<CommentJSON> comments) {
        this.comments = comments;
    }


    /**
     * @author qmbx
     */
    static public class CommentJSON {
        Integer id;
        Integer userId;
        String head;
        String name;
        String content;
        Integer approveNum;
        Integer isQuestionOwner;
        Integer isAnswerer;
        List<ReplyJSON> reolies = new ArrayList<>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getApproveNum() {
            return approveNum;
        }

        public void setApproveNum(Integer approveNum) {
            this.approveNum = approveNum;
        }

        public Integer getIsQuestionOwner() {
            return isQuestionOwner;
        }

        public void setIsQuestionOwner(Integer isQuestionOwner) {
            this.isQuestionOwner = isQuestionOwner;
        }

        public Integer getIsAnswerer() {
            return isAnswerer;
        }

        public void setIsAnswerer(Integer isAnswerer) {
            this.isAnswerer = isAnswerer;
        }

        public List<ReplyJSON> getReolies() {
            return reolies;
        }

        public void setReolies(List<ReplyJSON> reolies) {
            this.reolies = reolies;
        }
    }

    static public class  ReplyJSON {
        Integer id;
        Integer userId;
        String head;
        String name;
        String replyTo;
        String content;
        Integer approveNum;
        Integer isQuestionOwner;
        Integer isAnswerer;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReplyTo() {
            return replyTo;
        }

        public void setReplyTo(String replyTo) {
            this.replyTo = replyTo;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getApproveNum() {
            return approveNum;
        }

        public void setApproveNum(Integer approveNum) {
            this.approveNum = approveNum;
        }

        public Integer getIsQuestionOwner() {
            return isQuestionOwner;
        }

        public void setIsQuestionOwner(Integer isQuestionOwner) {
            this.isQuestionOwner = isQuestionOwner;
        }

        public Integer getIsAnswerer() {
            return isAnswerer;
        }

        public void setIsAnswerer(Integer isAnswerer) {
            this.isAnswerer = isAnswerer;
        }

    }
}



