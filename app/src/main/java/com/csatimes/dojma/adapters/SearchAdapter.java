package com.csatimes.dojma.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ContactItem;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.models.TypeItem;
import com.csatimes.dojma.utilities.ColorList;
import com.csatimes.dojma.viewholders.ContactItemViewHolder;
import com.csatimes.dojma.viewholders.EventItemViewHolder;
import com.csatimes.dojma.viewholders.GazetteItemViewHolder;
import com.csatimes.dojma.viewholders.HeraldSearchViewHolder;
import com.csatimes.dojma.viewholders.LinkItemViewHolder;
import com.csatimes.dojma.viewholders.MessItemViewHolder;
import com.csatimes.dojma.viewholders.SimpleTextViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_TITLE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_EVENT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_GAZETTE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_HERALD_ARTICLE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_LINK;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_MESS;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_POSTER;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_TITLE;

/**
 * adapter to place articles,gazettes in the search rv
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TypeItem> results = new ArrayList<>();
    private Context context;
    private Date currentDate;

    public SearchAdapter(Context context, List<TypeItem> results) {
        this.results = results;
        this.context = context;
        this.currentDate = null;
    }

    public SearchAdapter(List<TypeItem> results, Context context, Date currentDate) {
        this.results = results;
        this.context = context;
        this.currentDate = currentDate;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case CONTACT_ITEM_TYPE_TITLE:
            case SEARCH_ITEM_TYPE_TITLE:
                view = inflater.inflate(R.layout.item_format_simple_text, parent, false);
                viewHolder = new SimpleTextViewHolder(view);
                break;
            case SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE:
                view = inflater.inflate(R.layout.item_format_search_herald, parent, false);
                viewHolder = new HeraldSearchViewHolder(view);
                break;

            case SEARCH_ITEM_TYPE_HERALD_ARTICLE:
                view = inflater.inflate(R.layout.item_format_search_herald, parent, false);
                viewHolder = new HeraldSearchViewHolder(view);
                break;
            case SEARCH_ITEM_TYPE_GAZETTE:
                view = inflater.inflate(R.layout.item_format_search_gazette, parent, false);
                viewHolder = new GazetteItemViewHolder(view);
                break;
            case SEARCH_ITEM_TYPE_EVENT:
                view = inflater.inflate(R.layout.item_format_event, parent, false);
                viewHolder = new EventItemViewHolder(view);
                break;
            case CONTACT_ITEM_TYPE_CONTACT:
            case SEARCH_ITEM_TYPE_CONTACT:
                view = inflater.inflate(R.layout.item_format_contact, parent, false);
                viewHolder = new ContactItemViewHolder(view, context);
                break;
            case SEARCH_ITEM_TYPE_LINK:
                view = inflater.inflate(R.layout.item_format_links, parent, false);
                viewHolder = new LinkItemViewHolder(view, context);
                break;
            case SEARCH_ITEM_TYPE_MESS:
                view = inflater.inflate(R.layout.item_format_mess_menu, parent, false);
                viewHolder = new MessItemViewHolder(view);
                break;
            case SEARCH_ITEM_TYPE_POSTER:
                // view = inflater.inflate(R.layout.item_format_links, parent, false);
                //viewHolder = new PosIte(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == SEARCH_ITEM_TYPE_TITLE || holder.getItemViewType() == CONTACT_ITEM_TYPE_TITLE) {
            SimpleTextViewHolder stvh = (SimpleTextViewHolder) holder;
            stvh.text.setText((String) results.get(position).getValue());
        } else if (holder.getItemViewType() == SEARCH_ITEM_TYPE_HERALD_ARTICLE || holder.getItemViewType() == SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE) {
            HeraldSearchViewHolder hsvh = (HeraldSearchViewHolder) holder;
            HeraldItem foo = (HeraldItem) results.get(position).getValue();
            hsvh.title.setText(foo.getTitle());
            hsvh.date.setText(foo.getUpdateDate());
            hsvh.simpleDraweeView.setImageURI(Uri.parse(foo.getImageURL()));
        } else if (holder.getItemViewType() == SEARCH_ITEM_TYPE_GAZETTE) {
            GazetteItemViewHolder givh = (GazetteItemViewHolder) holder;
            GazetteItem foo = (GazetteItem) results.get(position).getValue();
            givh.title.setText(foo.getTitle() + "\n" + foo.getReleaseDateFormatted());
            givh.image.setImageURI(foo.getImageUrl());

        } else if (holder.getItemViewType() == SEARCH_ITEM_TYPE_EVENT) {
            EventItemViewHolder eivh = (EventItemViewHolder) holder;
            EventItem foo = (EventItem) results.get(position).getValue();
            eivh.title.setText(foo.getTitle());
            eivh.location.setText(foo.getLocation());
            eivh.dateTime.setText(foo.getStartDateFormatted() + "\n" + foo.getStartTimeFormatted());
            eivh.desc.setText(foo.getDesc());
            eivh.up.setVisibility(View.INVISIBLE);
            eivh.down.setVisibility(View.INVISIBLE);
            setColor(foo, eivh);
        } else if (holder.getItemViewType() == SEARCH_ITEM_TYPE_CONTACT || holder.getItemViewType() == CONTACT_ITEM_TYPE_CONTACT) {
            ContactItemViewHolder civh = (ContactItemViewHolder) holder;
            ContactItem foo = (ContactItem) results.get(position).getValue();
            civh.contactItem = foo;
            civh.contactName.setText(foo.getName());
            civh.contactSub1.setText(foo.getSub1());
            civh.contactSub2.setText(foo.getSub2());
            if (foo.getIcon() != null) {
                civh.contactIcon.setImageURI(Uri.parse(foo.getIcon()));
            } else {
                civh.contactIcon.setImageURI(Uri.parse("res://" + context.getPackageName()
                        + "/" + R.drawable.ic_contact));
            }
        } else if (holder.getItemViewType() == SEARCH_ITEM_TYPE_LINK) {
            LinkItemViewHolder livh = (LinkItemViewHolder) holder;
            LinkItem foo = (LinkItem) results.get(position).getValue();
            livh.linkItem = foo;
            livh.title.setText(foo.getTitle());
            livh.url.setText(foo.getUrl());
        } else if (holder.getItemViewType() == SEARCH_ITEM_TYPE_MESS) {
            MessItemViewHolder mivh = (MessItemViewHolder) holder;
            MessItem foo = (MessItem) results.get(position).getValue();
            mivh.title.setText(foo.getTitle());
            mivh.image.setImageURI(Uri.parse(foo.getImageUrl()));
        }

    }

    private void setColor(EventItem ei, EventItemViewHolder eivh) {
        if (ei.getStartDateObj() != null) {
            long diff = -currentDate.getTime() + ei.getStartDateObj().getTime();
            int color;

            if (diff <= 0) {
                //Irrespective of whether alarm was set, switch is unchecked since it isn't required anymore
                color = ContextCompat.getColor(context, ColorList.NO_PRIORITY);
                eivh.aSwitch.setChecked(false);

            } else {

                eivh.aSwitch.setChecked(ei.isAlarmSet());

                long DAY = 24 * 60 * 60 * 1000;
                if (diff > 0 && diff <= DAY) {
                    color = ContextCompat.getColor(context, ColorList.HIGHEST_PRIORITY);
                } else if (diff > DAY && diff <= 3 * DAY) {
                    color = ContextCompat.getColor(context, ColorList.HIGHER_PRIORITY);
                } else if (diff > 3 * DAY && diff <= 7 * DAY) {
                    color = ContextCompat.getColor(context, ColorList.HIGH_PRIORITY);
                } else if (diff > 7 * DAY && diff <= 14 * DAY) {
                    color = ContextCompat.getColor(context, ColorList.NORMAL_PRIORITY);
                } else if (diff > 14 * DAY && diff <= 30 * DAY) {
                    color = ContextCompat.getColor(context, ColorList.LOW_PRIORITY);
                } else if (diff > 30 * DAY && diff <= 365 * DAY) {
                    color = ContextCompat.getColor(context, ColorList.LOWER_PRIORITY);
                } else {
                    color = ContextCompat.getColor(context, ColorList.LOWEST_PRIORITY);
                }

            }

            eivh.status.setColorFilter(color);
            eivh.dateTime.setTextColor(color);


        } else {
            eivh.dateTime.setTextColor(ContextCompat.getColor(context, ColorList.LOWEST_PRIORITY));
            eivh.status.setColorFilter(Color.GRAY);
            eivh.aSwitch.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        return results.get(position).getType();
    }
}
