package io.github.evilofsoul.openwol;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.evilofsoul.openwol.core.Machine;

/**
 * Created by Yevhenii on 13.08.2016.
 */
public class MachineListAdapter extends RecyclerView.Adapter<MachineListAdapter.MachineViewHolder> {
    private List<Machine> machineList;
    private MachineListOnItemClickListener onItemClickListener;

    public MachineListAdapter(List<Machine> machineList) {
        this.machineList = machineList;
    }

    public MachineListAdapter() {
        this.machineList = new ArrayList<>();
    }

    public void setOnItemClickListener(MachineListOnItemClickListener onItemClickListener) {
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
    public MachineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MachineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MachineViewHolder holder, int position) {
        Machine machine = machineList.get(position);
        holder.name.setText(machine.getName());
        holder.mac.setText(machine.getMac().toString());
    }

    @Override
    public int getItemCount() {
        return machineList.size();
    }

    class MachineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView mac;
        private ImageButton sendPacketButton;
        private View container;
        //private MachineListOnItemClickListener onItemClickListener;

        public MachineViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.machineName);
            mac = (TextView) itemView.findViewById(R.id.machineMac);
            sendPacketButton = (ImageButton) itemView.findViewById(R.id.machineSettingsButton);
            container = itemView;

            sendPacketButton.setOnClickListener(this);
            container.setOnClickListener(this);
        }

//        public void setOnItemClickListener(MachineListOnItemClickListener onItemClickListener) {
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

    public interface MachineListOnItemClickListener {
        void onSettingsButtonClick(Machine machine, int position);
        void onItemClick(Machine machine, int position);
    }
}
