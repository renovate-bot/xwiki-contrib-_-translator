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
package org.xwiki.contrib.machinetranslation.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.machinetranslation.MachineTranslation;
import org.xwiki.contrib.machinetranslation.MachineTranslationConfiguration;
import org.xwiki.contrib.machinetranslation.MachineTranslationException;
import org.xwiki.contrib.machinetranslation.Translator;
import org.xwiki.contrib.machinetranslation.TranslatorManager;
import org.xwiki.contrib.machinetranslation.Usage;
import org.xwiki.contrib.machinetranslation.model.GlossaryInfo;
import org.xwiki.contrib.machinetranslation.model.LocalePair;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.script.service.ScriptService;
import org.xwiki.security.authorization.ContextualAuthorizationManager;
import org.xwiki.security.authorization.Right;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.api.Document;

/**
 * Script service used to access the Translator service.
 *
 * @version $Id$
 */
@Component
@Named("machinetranslation")
@Singleton
public class MachineTranslationScriptService implements ScriptService
{
    /**
     * Translator configuration.
     */
    @Inject
    private MachineTranslationConfiguration translatorConfiguration;

    /**
     * Translator manager allowing to retrieve a specific translator implementation.
     */
    @Inject
    private TranslatorManager translatorManager;

    /**
     * The authorization manager.
     */
    @Inject
    private ContextualAuthorizationManager authorizationManager;

    @Inject
    private Provider<XWikiContext> xwikiContextProvider;

    /**
     * Returns the original document reference and locale of a given document.
     *
     * @param reference a given reference
     * @return reference to original document
     * @throws MachineTranslationException in case an error occurs
     */
    public DocumentReference getOriginalDocumentReference(EntityReference reference)
        throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return null;
        }
        return translator.getOriginalDocumentReference(reference);
    }

    /**
     * @param reference a given reference
     * @return original Document
     * @throws MachineTranslationException in case an error occurs
     */
    public Document getOriginalDocument(EntityReference reference) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return null;
        }
        return new Document(translator.getOriginalDocument(reference), xwikiContextProvider.get());
    }

    /**
     * @param doc a given Document
     * @return true if the passed Document is the original one
     * @throws MachineTranslationException in case an error occurs
     */
    public boolean isOriginalDocument(Document doc) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return false;
        }
        return translator.isOriginalDocument(doc);
    }

    /**
     * @param translation a given MachineTranslation
     * @return true if the translation is the current document
     * @throws MachineTranslationException in case an error occurs
     */
    public boolean isCurrentDocument(MachineTranslation translation) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return false;
        }
        return translator.isCurrentDocument(translation);
    }

    /**
     * @param reference a document reference
     * @return Locale the real locale of the original document
     * @throws MachineTranslationException in case an error occurs
     */
    public Locale getOriginalDocumentRealLocale(EntityReference reference) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return null;
        }
        return translator.getOriginalDocumentRealLocale(reference);
    }

    /**
     * Retrieves existing translation pages of a page with a given reference.
     *
     * @param reference Reference of a page
     * @return set of existing translations for that page
     * @throws MachineTranslationException in case an error occurs
     */
    public List<MachineTranslation> getTranslations(DocumentReference reference) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return null;
        }
        return translator.getTranslations(reference);
    }

    /**
     * @param reference a document reference
     * @param locale a locale
     * @return translation of the given reference in given locale, if it exists, null otherwise
     * @throws MachineTranslationException in cas an error occurs
     */
    public MachineTranslation getTranslation(DocumentReference reference, Locale locale)
        throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return null;
        }
        return translator.getTranslation(reference, locale);
    }

    /**
     * Checks if a given reference can be translated according to the rules defined in the translator configuration.
     *
     * @param reference Reference of a page
     * @return true if the given reference can be translated, false otherwise
     * @throws MachineTranslationException in case an error occurs
     */
    public boolean isTranslatable(DocumentReference reference) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return false;
        }
        return translatorManager.getTranslator().isTranslatable(reference);
    }

    /**
     * Checks if the current user can translate a page.
     *
     * @param reference A given page reference
     * @return true if the current user can create page
     * @throws MachineTranslationException in case an error occurs
     */
    public boolean canTranslate(DocumentReference reference) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return false;
        }
        return translatorManager.getTranslator().canTranslate(reference);
    }

    /**
     * Checks if the current user can translate a page in a given locale.
     *
     * @param reference A given page reference
     * @param toLocale Locale to be translated to
     * @return true if user can translate page, false otherwise
     * @throws MachineTranslationException in case an error occurs
     */
    public boolean canTranslate(DocumentReference reference, Locale toLocale) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return false;
        }
        return translatorManager.getTranslator().canTranslate(reference, toLocale);
    }

    /**
     * Returns true in case the location of the translations of a given reference is the same as the original page.
     *
     * @param reference A given page reference
     * @return true if location strategy is "same location"
     * @throws MachineTranslationException in case an error occurs
     */
    public boolean isSameNameTranslationNamingStrategy(EntityReference reference) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return false;
        }
        return translator.isSameNameTranslationNamingStrategy(reference);
    }

    /**
     * Translates given page into iven locale.
     *
     * @param reference A page reference
     * @param toLocale A locale
     * @return Translation page reference
     * @throws MachineTranslationException in case an error occurs
     */
    public EntityReference translate(EntityReference reference, Locale toLocale) throws MachineTranslationException
    {
        return translatorManager.getTranslator().translate(reference, toLocale);
    }

    /**
     * Translates given content from given locale to another locale, optionally with html.
     *
     * @param content Given content
     * @param from Given original locale
     * @param to To target locale
     * @param html If translation should take html into account
     * @return translated content
     * @throws MachineTranslationException in case an error occurs
     */
    public String translate(String content, Locale from, Locale to, boolean html) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        return translator.translate(content, from, to, html);
    }

    /**
     * Returns TranslatorManager.
     *
     * @return TranslatorManager
     */
    public TranslatorManager getManager()
    {
        if (this.authorizationManager.hasAccess(Right.PROGRAM)) {
            return this.translatorManager;
        } else {
            return null;
        }
    }

    /**
     * Returns available translators.
     *
     * @return available translators
     */
    public Set<String> getAvailableTranslators()
    {
        return translatorManager.getAvailableTranslators();
    }

    /**
     * Returns current translator name.
     *
     * @param hint Component name
     * @return current translator name
     */
    public String getName(String hint)
    {
        Translator translator = translatorManager.getTranslator(hint);
        if (translator == null) {
            return "";
        }
        return translator.getName();
    }

    /**
     * Returns Usage statistics in current period.
     *
     * @return Usage in current period
     * @throws MachineTranslationException in case an error occurs
     */
    public Usage getUsage() throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return null;
        }
        return translator.getUsage();
    }

    /**
     * Computes the location of a translation based on the original document and the translation title.
     *
     * @param originalDocument Reference of the original document
     * @param translationTitle Title of the translation
     * @param translationLocale Translation locale
     * @return reference of the translation page
     * @throws MachineTranslationException in case an error occurs
     */
    public EntityReference computeTranslationReference(DocumentReference originalDocument, String translationTitle,
        Locale translationLocale) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (translator == null) {
            return originalDocument;
        }
        return translator.computeTranslationReference(originalDocument, translationTitle, translationLocale);
    }

    /**
     * Returns list of existing glossaries.
     *
     * @return list of glossaries
     * @throws MachineTranslationException in case an error occurs
     */
    public List<GlossaryInfo> getGlossaries() throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (this.authorizationManager.hasAccess(Right.PROGRAM)) {
            return translator.getGlossaries();
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * Returns list of glossary entries for given glossary.
     *
     * @param id A given glossary identifier
     * @return List of glossary entries
     * @throws MachineTranslationException in case an error occurs
     */
    public Map<String, String> getGlossaryEntryDetails(String id) throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (this.authorizationManager.hasAccess(Right.PROGRAM)) {
            return translator.getGlossaryEntries(id);
        } else {
            return new HashMap<>(0);
        }
    }

    /**
     * List the glossary locale pairs supported by the translator.
     *
     * @return list of locale pairs
     * @throws MachineTranslationException in case an error occurs
     */
    public Map<LocalePair, Boolean> getGlossaryLocalePairSupport() throws MachineTranslationException
    {
        Translator translator = translatorManager.getTranslator();
        if (this.authorizationManager.hasAccess(Right.PROGRAM)) {
            return translator.getGlossaryLocalePairSupport();
        } else {
            return new HashMap<>(0);
        }
    }
}
