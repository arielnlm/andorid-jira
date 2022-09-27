package com.example.projekat1.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.projekat1.fragments.FirstFragment;
import com.example.projekat1.fragments.ForthFragment;
import com.example.projekat1.fragments.SecondFragment;
import com.example.projekat1.fragments.ThirdFragment;
import com.example.projekat1.fragments.TicketListFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 3;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;

    public TabPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_1: fragment = new TicketListFragment(0); break;
            case FRAGMENT_2: fragment = new TicketListFragment(1); break;
            default: fragment = new TicketListFragment(2); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case FRAGMENT_1: return "BUGS";
            case FRAGMENT_2: return "IN PROGRESS";
            default: return  "DONE";
        }
    }
}
