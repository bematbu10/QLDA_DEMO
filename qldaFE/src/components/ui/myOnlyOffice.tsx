import pocketbaseService from "@/services/PocketBaseService";
import {DocumentEditor} from "@onlyoffice/document-editor-react";

function onDocumentReady(event) {
  console.log("Document is loaded");
}

function onLoadComponentError(errorCode, errorDescription) {
  switch (errorCode) {
  case -1: // Unknown error loading component
    console.log(errorDescription);
    break;

  case -2: // Error load DocsAPI from http://documentserver/
    console.log(errorDescription);
    break;

  case -3: // DocsAPI is not defined
    console.log(errorDescription);
    break;
  }
}

async function onRequestSaveAs(id, data) {
  const fileBlob = await downloadFileFromUrl(data.data.url);
  const fileName = data.data.title;
  const attachmentFileUpdate = await pocketbaseService.updateFile(id, fileBlob, fileName);
}

const downloadFileFromUrl = async (url: string) => {
  // Route OnlyOffice DS downloads through Vite proxy to avoid CORS in dev
  const proxiedUrl = url.startsWith("http://192.168.100.98:8080/")
    ? url.replace("http://192.168.100.98:8080/", "/oo-cache/")
    : url;
  const response = await fetch(proxiedUrl);
  if (!response.ok) {
    throw new Error(`Download failed: ${response.status}`);
  }
  return await response.blob();
};

export default function MyOnlyOffice( props: {fileUrl: string, fileName: string, id: string }) {

  return (
    <DocumentEditor
      id="docxEditor"
      documentServerUrl="http://192.168.100.98:8080/"
      config={{
        document: {
          fileType: "docx",
          key: props.id,  
          title: props.fileName,
          url: props.fileUrl,
        },
        documentType: "word",
        editorConfig: {
          callbackUrl: "http://192.168.100.99:8098/api/public/only-office/support-documents/edit",
            // callbackUrl: `${window.location.origin}/api/onlyoffice/callback`
        },
      }}
      events_onDocumentReady={onDocumentReady}
      onLoadComponentError={onLoadComponentError}
      events_onRequestSaveAs={(e) => onRequestSaveAs(props.id, e)}
    />
  )
}