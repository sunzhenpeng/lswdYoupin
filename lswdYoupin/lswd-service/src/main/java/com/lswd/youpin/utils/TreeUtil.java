package com.lswd.youpin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.model.lsyp.MaterialCategory;
import com.lswd.youpin.model.vo.Nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liruilong on 2017/6/6.
 */
public class TreeUtil {

    public static List<Nodes> getDataToTree(List<Nodes> resources, Integer parentId) {

        List<Nodes> rootTrees = new ArrayList<>();
        JSONObject jsonObject1= JSON.parseObject("{selected:true,expanded:true}");
        JSONObject jsonObject2= JSON.parseObject("{selected:false,expanded:false}");
        for (Nodes tree : resources) {
            if (tree.getpId() == parentId) {
                rootTrees.add(tree);
            }
            for (Nodes t : resources) {
                if (t.getpId() == tree.getId()) {
                    if (tree.getNodes() == null) {
                        List<Nodes> myChildrens = new ArrayList<>();
                        myChildrens.add(t);
                        tree.setNodes(myChildrens);
                    } else {
                        t.setState(jsonObject2);
                        tree.getNodes().add(t);
                    }
                }
            }
        }
        for (Nodes resources1 : rootTrees) {
            if (resources1.getNodes() == null) {
                resources1.setNodes(new ArrayList<>());
            }
        }
        if(rootTrees.get(0).getNodes()==null||rootTrees.get(0).getNodes().size()==0){
            rootTrees.get(0).setState(jsonObject1);
        }else {
            rootTrees.get(0).getNodes().get(0).setState(jsonObject1);
        }
        return rootTrees;
    }

    public static List<MaterialCategory> getMaterialToTree(List<MaterialCategory> categories) {

        List<MaterialCategory> rootTrees = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            MaterialCategory tree = categories.get(i);
            if (tree.getParentId() == 0) {
                rootTrees.add(tree);
            }
            for (int j = 0; j < categories.size(); j++) {
                MaterialCategory category = categories.get(j);
                if (category.getParentId() == tree.getId()) {
                    if (tree.getChildrens() != null) {
                        tree.getChildrens().add(category);
                    } else {
                        List<MaterialCategory> childrens = new ArrayList<>();
                        categories.add(category);
                        tree.setChildrens(childrens);
                    }
                }
            }

        }
        return rootTrees;
    }
}
