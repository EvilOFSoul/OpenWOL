package io.github.evilofsoul.openwol.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.evilofsoul.openwol.R;
import io.github.evilofsoul.openwol.core.Machine;

/**
 * Created by Yevhenii on 13.08.2016.
 */
public class MachineListAdapter extends RecyclerView.Adapter {
    private List<Machine> machineList;
    private OnItemClickListener onItemClickListener;
    private final static int TYPE_FOOTER = Integer.MAX_VALUE;

    public MachineListAdapter(List<Machine> machineList) {
        this.machineList = machineList;
    }

    public MachineListAdapter() {
        this.machineList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<Machine> getMachineList() {
        return machineList;
    }

    public void setMachineList(List<Machine> machineList) {
        this.machineList = machineList;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType != MachineListAdapter.TYPE_FOOTER) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new MachineViewHolder(view);
        } else {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MachineViewHolder) {
            MachineViewHolder machineViewHolder = (MachineViewHolder) holder;
            Machine machine = machineList.get(position);
            machineViewHolder.name.setText(machine.getName());
            machineViewHolder.mac.setText(machine.getMac().toString());
        }
    }

    @Override
    public int getItemCount() {
        return machineList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == machineList.size()){
            return MachineListAdapter.TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    class MachineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView mac;
        private ImageButton sendPacketButton;
        private View container;
        //private OnItemClickListener onItemClickListener;

        public MachineViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.machineName);
            mac = (TextView) itemView.findViewById(R.id.machineMac);
            sendPacketButton = (ImageButton) itemView.findViewById(R.id.machineSettingsButton);
            container = itemView;

            sendPacketButton.setOnClickListener(this);
            container.setOnClickListener(this);
        }

//        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//            this.onItemClickListener = onItemClickListener;
//        }

        @Override
        public void onClick(View view) {
            int itemId = getAdapterPosition();
            if (view.getId() == R.id.machineSettingsButton) {
                onItemClickListener.onSettingsButtonClick(machineList.get(itemId), itemId);
            } else {
                onItemClickListener.onItemClick(machineList.get(itemId), itemId);
            }
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onSettingsButtonClick(Machine machine, int position);
        void onItemClick(Machine machine, int position);
    }
}
