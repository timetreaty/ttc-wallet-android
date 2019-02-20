package com.example.administrator.ttc;

import java.util.List;

/**
 * Created by Administrator on 2018/11/12/012.
 */

public interface PermissionListener {
    void granted();
    void denied(List<String> deniedList);
}
