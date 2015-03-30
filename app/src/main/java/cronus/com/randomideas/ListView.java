package cronus.com.randomideas;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import android.support.v4.app.Fragment;

/**
 * Created by Daven on 3/28/2015.
 */
public class ListView extends Fragment{
    private String[] content;

    public ListView(){

    }

    public void setArray(String[] content){
        this.content = content;
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listview, container, false);



        android.widget.ListView list = (android.widget.ListView) root.findViewById(android.R.id.list);

        ListViewAdapter listAdapter = new ListViewAdapter(getActivity(), content);
        list.setAdapter(listAdapter);



        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.attachToListView(list, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                Log.d("ListViewFragment", "onScrollDown()");
            }

            @Override
            public void onScrollUp() {
                Log.d("ListViewFragment", "onScrollUp()");
            }
        }, new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d("ListViewFragment", "onScrollStateChanged()");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("ListViewFragment", "onScroll()");
            }
        });

        return root;
    }
}
