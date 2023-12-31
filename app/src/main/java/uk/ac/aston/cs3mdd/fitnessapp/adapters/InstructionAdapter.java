package uk.ac.aston.cs3mdd.fitnessapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;

public class InstructionAdapter extends SliderViewAdapter<InstructionAdapter.InstructionViewHolder> {

    private List<String> instructions;

    private LayoutInflater inflater;

    public InstructionAdapter(List<String> instructions){
        this.instructions = instructions;
    }
    @Override
    public InstructionViewHolder onCreateViewHolder(ViewGroup parent) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.instuction_items, parent, false);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstructionViewHolder viewHolder, int position) {
        String instruction = instructions.get(position);
        viewHolder.instructionText.setText(instruction);
    }

    @Override
    public int getCount() {
        return instructions.size();
    }

    static class InstructionViewHolder extends SliderViewAdapter.ViewHolder {
        private TextView instructionText;
        public InstructionViewHolder(View itemView) {
            super(itemView);
            instructionText = itemView.findViewById(R.id.instruction_text);
        }
    }
}
