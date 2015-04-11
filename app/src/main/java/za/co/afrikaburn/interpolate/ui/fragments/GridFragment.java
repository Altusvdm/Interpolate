package za.co.afrikaburn.interpolate.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.model.Cube;
import za.co.afrikaburn.interpolate.model.CubeMode;
import za.co.afrikaburn.interpolate.ui.adapters.CubeGridAdapter;

/**
 * Created by Altus on 2015/04/11.
 */
public class GridFragment extends BaseFragment {

    View rootView;

    @InjectView(R.id.main_grid_view)
    GridView cubeGrid;

    CubeGridAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.fragment_grid, container, false);

            ButterKnife.inject(this, rootView);

            for (int t = 0; t < 3; t++) {
                for (int i = 0; i < 5; i++) {
                    int absPos = (t*5) + i;
                    Cube newCube = new Cube();
                    newCube.identifier = absPos + "";
                    newCube.battery = (absPos/16.0f) * 100;
                    newCube.positionX = i;
                    newCube.positionY = t;
                    newCube.cubeMode = CubeMode.BLINKING;

                    Cube.saveCube(newCube);
                }
            }

            for (int i = 0; i < 2; i++) {
                int absPos = (3*5) + i;
                Cube newCube = new Cube();
                newCube.identifier = absPos + "";
                newCube.battery = (absPos/16.0f) * 100;
                newCube.positionX = i;
                newCube.positionY = 3;
                newCube.cubeMode = CubeMode.RAINBOW;

                Cube.saveCube(newCube);
            }


            adapter = new CubeGridAdapter(this.getActivity());
            cubeGrid.setAdapter(adapter);

            cubeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getMainActivity().showFragment(new BaseModeFragment());
                }
            });
        }

        return rootView;
    }
}
