package ru.mechaneg.basejava.model;

class ContentSection<T> extends AbstractSection {
    private T content;

    public T getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ContentSection{" +
                "content=" + content +
                '}';
    }

    public void setContent(T content) {
        this.content = content;
    }

    public ContentSection(T content) {
        this.content = content;
    }
}
