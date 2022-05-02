import React, { Component } from "react";

class OCRResult extends Component {
    render() {
        if (!this.props.ocrResult) {
            return <div>No OCR Data Found...</div>
        }
        return (
            <div id="ocrResult">
                {this.props.ocrResult.data}
            </div>
        );
    }
}

export default OCRResult;