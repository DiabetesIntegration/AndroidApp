package com.example.kbb12.dms.customListView;

import android.view.View;

/**
 * Created by kbb12 on 04/04/2017.
 */

public class CustomListViewControllerFactory implements ICustomListViewControllerFactory {

    private IDeleteCustomItem model;

    public CustomListViewControllerFactory(IDeleteCustomItem model){
        this.model=model;
    }

    @Override
    public View.OnClickListener getCustomListViewController(int position) {
        return new CustomListViewController(position,model);
    }
}
