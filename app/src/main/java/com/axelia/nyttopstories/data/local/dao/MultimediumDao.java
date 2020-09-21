package com.axelia.nyttopstories.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.axelia.nyttopstories.data.model.Multimedium;
import java.util.List;

@Dao
public interface MultimediumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllItems(List<Multimedium> itemList);

}
