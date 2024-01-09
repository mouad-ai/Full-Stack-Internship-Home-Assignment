import { useState } from 'react';
import FileUpload from './api/FileUpload';
import ProcessingResults from './api/ProcessingResults';
import "../styles/globals.css"

const App = () => {
  const [file, setFile] = useState(null);
  const [processingResults, setProcessingResults] = useState(null);

  const handleFileChange = (selectedFile) => {
    setFile(selectedFile);
  };

  const handleProcess = async () => {
    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await fetch('http://localhost:8080/api/csv/upload', {
        method: 'POST',
        body: formData,
      });

      if (response.ok) {
        const result = await response.json();
        setProcessingResults(result);
      } else {
        console.error('Error processing file');
      }
    } catch (error) {
      console.error('Error processing file', error);
    }
  };

  return (
      <div>
        <h1>CSV Parser</h1>
        <FileUpload onFileChange={handleFileChange} />
        {file && <button onClick={handleProcess} disabled={!file} className="process-button">
          Process
        </button>
        }
        {processingResults && (
            <ProcessingResults
                employees={processingResults.employees}
                jobSummary={processingResults.averageSalaries}
            />
        )}
      </div>
  );
};

export default App;
