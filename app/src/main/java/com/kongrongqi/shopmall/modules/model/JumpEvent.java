package com.kongrongqi.shopmall.modules.model;

/**
 * 创建日期：2017/6/2 0002 on 15:50
 * 作者:penny
 */
public class JumpEvent {
    private int isJump;


    public JumpEvent(int b) {
        this.isJump = b;
    }

    public int codeJump() {
        return isJump;
    }

    public void setJump(int jump) {
        isJump = jump;
    }
}
