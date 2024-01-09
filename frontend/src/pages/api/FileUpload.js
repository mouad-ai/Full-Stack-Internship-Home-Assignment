import { useState } from 'react';

const FileUpload = ({ onFileChange }) => {
    const handleFileChange = (event) => {
        const file = event.target.files[0];
        onFileChange(file);
    };

    return (
        <div>
            Choose File :
            <input type="file" onChange={handleFileChange} className={"process-button"}/>
        </div>
    );
};

export default FileUpload;
