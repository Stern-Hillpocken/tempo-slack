export class PopupFeedback {
    constructor(
        public message: string,
        public type: "error" | "info" | "valid" | "warning"
    ){}
}