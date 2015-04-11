package za.co.afrikaburn.interpolate.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import za.co.afrikaburn.interpolate.model.Cube;
import za.co.afrikaburn.interpolate.ui.views.CubeView;

/**
 * Created by Altus on 2015/02/01.
 */
public class CubeGridAdapter extends BaseAdapter {
    private Context context;
    private List<Cube> cubes;

    public CubeGridAdapter(Context context) {
        super();

        this.context = context;

        cubes = Cube.getAllCubes();
    }

    @Override
    public int getCount() {
        if (cubes != null) {
            return cubes.size();
        }
        return 0;
    }

    @Override
    public Cube getItem(int i) {
        if (cubes != null) {
            return cubes.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            CubeView cubeView = new CubeView(context, null);
            view = cubeView;
        }

        ((CubeView)view).setCube(getItem(i));

        return view;
    }
}
