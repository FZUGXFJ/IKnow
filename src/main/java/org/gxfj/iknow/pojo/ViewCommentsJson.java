package org.gxfj.iknow.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qmbx
 * 5月2日
 */
public class ViewCommentsJson {
    Integer resultCode;
    Integer commentNum;
    List<CommentJson> comments = new ArrayList<>();

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public List<CommentJson> getComments() {
        return comments;
    }

    public void setComments(List<CommentJson> comments) {
        this.comments = comments;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * @author qmbx
     */
    static public class CommentJson {
        Integer id;
        Integer userId;
        String head;
        String name;
        String content;
        Integer approveNum;
        Integer isQuestionOwner;
        Integer isAnswerer;
        List<ReplyJson> replies = new ArrayList<>();
        Integer replyNum;
        String time;

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

        public List<ReplyJson> getReplies() {
            return replies;
        }

        public void setReplies(List<ReplyJson> replies) {
            this.replies = replies;
        }

        public Integer getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(Integer replyNum) {
            this.replyNum = replyNum;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    static public class ReplyJson {
        Integer id;
        Integer userId;
        String head;
        String name;
        String targetName;
        Integer targetId;
        String content;
        Integer approveNum;
        Integer isQuestionOwner;
        Integer isAnswerer;
        String time;

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

        public String getTargetName() {
            return targetName;
        }

        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }

        public Integer getTargetId() {
            return targetId;
        }

        public void setTargetId(Integer targetId) {
            this.targetId = targetId;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}



