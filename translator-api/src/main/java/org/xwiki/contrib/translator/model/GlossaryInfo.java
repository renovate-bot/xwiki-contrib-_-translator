/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.translator.model;

/**
 * @version $Id$
 * Struct used to describe a glossary for a translator.
 */
public class GlossaryInfo
{
    private final String glossaryId;

    private final String name;

    private final boolean ready;

    private final String sourceLang;

    private final String targetLang;

    private final long entryCount;

    public GlossaryInfo(String glossaryId, String name, boolean ready, String sourceLang, String targetLang,
        long entryCount)
    {
        this.glossaryId = glossaryId;
        this.name = name;
        this.ready = ready;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        this.entryCount = entryCount;
    }

    /**
     * @return the internal ID of the translator glossary.
     */
    public String getGlossaryId()
    {
        return glossaryId;
    }

    /**
     * @return the name of the glossary.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the state of the translator glossary. Return 'true' if the glossary is ready to use into the translator.
     * else it returns false.
     */
    public boolean isReady()
    {
        return ready;
    }

    /**
     * @return the source lang of the glossary.
     */
    public String getSourceLang()
    {
        return sourceLang;
    }

    /**
     * @return the target lang of the glossary.
     */
    public String getTargetLang()
    {
        return targetLang;
    }

    /**
     * @return the number of item into this glossary.
     */
    public long getEntryCount()
    {
        return entryCount;
    }
}
