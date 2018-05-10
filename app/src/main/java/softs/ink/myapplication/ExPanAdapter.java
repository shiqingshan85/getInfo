package softs.ink.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.security.acl.Group;
import java.util.ArrayList;

/**
 * Created by qq230 on 2018/5/9.
 */

public class ExPanAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> groups;
    private ArrayList<ArrayList<IMoocBean.DataBean>> childs;
    private Activity activity;

    public ExPanAdapter(ArrayList<String> groups, ArrayList<ArrayList<IMoocBean.DataBean>> childs, Activity activity) {
        this.groups = groups;
        this.childs = childs;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return (groups.isEmpty()?0:groups.size());
    }

    @Override
    public int getChildrenCount(int i) {
        return childs.size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childs.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder viewHolder;
        if (view==null){
            view= LayoutInflater.from(activity).inflate(R.layout.group,null);
            viewHolder=new GroupViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder= (GroupViewHolder) view.getTag();
        }
        viewHolder.title.setText(groups.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.child, null);
            viewHolder = new ChildViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) view.getTag();
        }
        viewHolder.desc.setText(childs.get(i).get(i1).getDescription());
        viewHolder.learner.setText(childs.get(i).get(i1).getLearner());
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class GroupViewHolder{
        TextView title;

        public GroupViewHolder(View view) {
            title= (TextView) view.findViewById(R.id.group_tv);
        }
    }

    class ChildViewHolder{
        TextView desc;
        TextView learner;

        public ChildViewHolder(View view) {
            desc= (TextView) view.findViewById(R.id.child_description);
            learner= (TextView) view.findViewById(R.id.child_learner);
        }
    }
}
