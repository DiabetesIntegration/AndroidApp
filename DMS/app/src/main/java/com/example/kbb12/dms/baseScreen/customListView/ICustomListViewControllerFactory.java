package com.example.kbb12.dms.baseScreen.customListView;

import android.view.View;

/**
 * Created by kbb12 on 04/04/2017.
 */

public interface ICustomListViewControllerFactory {
    View.OnClickListener getCustomListViewController(int position);
}
