package limitless.com.br.parse.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import limitless.com.br.parse.R;
import limitless.com.br.parse.model.NotificationObject;

/**
 * Created by Márcio Sn on 22/12/2015.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<NotificationObject> itens;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public TextView timestamp;
        public ViewHolder(View v) {
            super(v);

            message = (TextView) v.findViewById(R.id.messageNotification);
            timestamp = (TextView) v.findViewById(R.id.timestampNotification);
        }
    }

    public Adapter(List<NotificationObject> itens) {
        this.itens = itens;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationObject notification = itens.get(position);
        holder.message.setText(notification.getMessage());
        holder.timestamp.setText(notification.getTimestamp().toString());
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}
