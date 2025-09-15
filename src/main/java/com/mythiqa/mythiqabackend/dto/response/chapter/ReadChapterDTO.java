package com.mythiqa.mythiqabackend.dto.response.chapter;

public class ReadChapterDTO {
    private GetChapterDTO currentChapter;
    private ChapterSummaryDTO prevChapter;
    private ChapterSummaryDTO nextChapter;

    private ReadChapterDTO(Builder builder) {
        this.currentChapter = builder.currentChapter;
        this.prevChapter = builder.prevChapter;
        this.nextChapter = builder.nextChapter;
    }

    public GetChapterDTO getCurrentChapter() {
        return currentChapter;
    }

    public ChapterSummaryDTO getPrevChapter() {
        return prevChapter;
    }

    public ChapterSummaryDTO getNextChapter() {
        return nextChapter;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private GetChapterDTO currentChapter;
        private ChapterSummaryDTO prevChapter;
        private ChapterSummaryDTO nextChapter;

        public Builder currentChapter(GetChapterDTO currentChapter) {
            this.currentChapter = currentChapter;
            return this;
        }

        public Builder prevChapter(ChapterSummaryDTO prevChapter) {
            this.prevChapter = prevChapter;
            return this;
        }

        public Builder nextChapter(ChapterSummaryDTO nextChapter) {
            this.nextChapter = nextChapter;
            return this;
        }

        public ReadChapterDTO build() {
            return new ReadChapterDTO(this);
        }
    }

    public static class ChapterSummaryDTO {
        private String chapterId;
        private String chapterName;
        private int chapterNumber;

        private ChapterSummaryDTO(Builder builder) {
            this.chapterId = builder.chapterId;
            this.chapterName = builder.chapterName;
            this.chapterNumber = builder.chapterNumber;
        }

        public String getChapterId() {
            return chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public int getChapterNumber() {
            return chapterNumber;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String chapterId;
            private String chapterName;
            private int chapterNumber;

            public Builder chapterId(String chapterId) {
                this.chapterId = chapterId;
                return this;
            }

            public Builder chapterName(String chapterName) {
                this.chapterName = chapterName;
                return this;
            }

            public Builder chapterNumber(int chapterNumber) {
                this.chapterNumber = chapterNumber;
                return this;
            }

            public ChapterSummaryDTO build() {
                return new ChapterSummaryDTO(this);
            }
        }
    }
}
