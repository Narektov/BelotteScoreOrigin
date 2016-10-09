package traveling.com.belottescore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder>  {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<Model> mModelRealmResults = realm.where(Model.class).findAll();
    Model model;

    public Adapter(RealmResults<Model> mModelRealmResults) {
        this.mModelRealmResults = mModelRealmResults;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        model = mModelRealmResults.get(position);



        holder.toldScore.setText(String.valueOf(model.toldScore));
        holder.yourScore.setText(String.valueOf(model.yourScore));
        holder.ourScore.setText(String.valueOf(model.ourScore));




    }

    @Override
    public int getItemCount() {
        return mModelRealmResults.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView ourScore;
        private TextView yourScore;
        private TextView toldScore;
        private ImageView deleteRow;
        public Holder(final View itemView) {
            super(itemView);
            ourScore = (TextView) itemView.findViewById(R.id.textView_ourScore);
            yourScore = (TextView) itemView.findViewById(R.id.textView_youScore);
            toldScore = (TextView) itemView.findViewById(R.id.textView_toldText);
            deleteRow = (ImageView)itemView.findViewById(R.id.imageView_delete);

            deleteRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View pView) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            mModelRealmResults.deleteFromRealm(getAdapterPosition());
                        }
                    });

                }
            });


        }


    }
}
