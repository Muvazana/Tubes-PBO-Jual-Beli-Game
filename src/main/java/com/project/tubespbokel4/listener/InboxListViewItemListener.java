package com.project.tubespbokel4.listener;


import com.project.tubespbokel4.model.toko.Transaction;

public interface InboxListViewItemListener {
    void onBtnApproveDoneClicked(Transaction transaction);
    void onBtnRejectCancelClicked(Transaction transaction);
}
