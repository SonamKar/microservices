package com.example.restapiexample.core.port.outbound;

import com.example.restapiexample.core.dbentity.Viewer;
import java.util.List;

public interface ViewerRepository {
    List<Viewer> getViewerDetails(String name);
}
