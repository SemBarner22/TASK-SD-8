package com.sd.bucket;

import com.sd.utils.InstantWrapper;
import com.sd.utils.Rpm;

public interface EventsBucket {
    void incEvent(InstantWrapper now);
    Rpm getRpm(InstantWrapper now);
    Rpm getAll();
}
