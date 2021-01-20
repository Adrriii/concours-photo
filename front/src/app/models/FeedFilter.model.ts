export class FeedFilter {
    constructor(
        public direction: string,
        public sort: string,
        public labels: string,
        public page: number,
        public nbPosts: number
    ) {
    }
}