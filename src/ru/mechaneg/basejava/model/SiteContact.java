package ru.mechaneg.basejava.model;

public class SiteContact extends AbstractContact {
    private String site;

    public SiteContact(String site) {
        this.site = site;
    }

    public String getSite() {
        return site;
    }

    @Override
    public String toString() {
        return "SiteContact{" +
                "site='" + site + '\'' +
                '}';
    }
}
