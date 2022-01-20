package com.project.tubespbokel4.listener;

import com.project.tubespbokel4.model.auth.WalletLog;

public interface AdminListViewItemListener {
    void onApprovaClicked(WalletLog  walletLog);
    void onRejectClicked(WalletLog  walletLog);
}
