package com.amaxilatis.ehr.model.list;

import java.util.List;

/**
 * A class that can list entities of type {@link A}.
 *
 * @param <A> The type of the entities that can be listed.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public interface Listable<A> {
    /**
     * Returns a {@link List} of entities of type {@link A}.
     */
    List<A> getList();
}
