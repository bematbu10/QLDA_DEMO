import PocketBase from 'pocketbase';


const POCKETBASE_URL = 'http://192.168.100.100:8090';
const API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb2xsZWN0aW9uSWQiOiJwYmNfMzE0MjYzNTgyMyIsImV4cCI6MTc1NzU3NTU5NywiaWQiOiJjY2E3ZWllaDdsM2d4Mm0iLCJyZWZyZXNoYWJsZSI6dHJ1ZSwidHlwZSI6ImF1dGgifQ.7GL9yw6B9TM3TB6FBoGyLzFGBJXVWqIHxDny4w0uTyo'; // Token từ PocketBase Admin


class PocketBaseService {

    
  private pb: PocketBase;

  constructor() {
    this.pb = new PocketBase(POCKETBASE_URL);
    this.setupAuth();
  }

  setupAuth() {
    const token = API_TOKEN;
    if (token) {
      this.pb.authStore.save(token, null);
    }
  }

  async login(email, password) {
    try {
      const authData = await this.pb.collection('users').authWithPassword(email, password);
      return authData;
    } catch (error) {
      console.error('Login failed:', error);
      throw error;
    }
  }

  logout() {
    this.pb.authStore.clear();
  }

  isAuthenticated() {
    return this.pb.authStore.isValid;
  }

  async uploadFile(file, fileName = null) {
    const formData = new FormData();
    
    formData.append('fileData', file);
    
    if (fileName) {
      formData.append('fileName', fileName);
    } else {
      formData.append('fileName', file.name);
    }

    try {
      const record = await this.pb.collection('AttachmentFile').create(formData);
      return record;
    } catch (error) {
      console.error('Error uploading file:', error);
      throw error;
    }
  }

  async getFiles(page = 1, perPage = 50) {
    try {
      const records = await this.pb.collection('AttachmentFile').getList(page, perPage, {
        sort: '-created',
        expand: 'owner'
      });
      return records;
    } catch (error) {
      console.error('Error fetching files:', error);
      throw error;
    }
  }

  async getFile(id) {
    try {
      const record = await this.pb.collection('AttachmentFile').getOne(id);
      return record;
    } catch (error) {
      console.error('Error fetching file:', error);
      throw error;
    }
  }

  async deleteFile(id) {
    try {
      await this.pb.collection('AttachmentFile').delete(id);
      return true;
    } catch (error) {
      console.error('Error deleting file:', error);
      throw error;
    }
  }

  getFileUrl(record) {
    return this.pb.files.getUrl(record, record.fileData);
  }

  getFileThumbUrl(record, thumbSize = '100x100') {
    return this.pb.files.getUrl(record, record.fileData, {
      thumb: thumbSize
    });
  }

  async updateFileName(id, newFileName) {
    try {
      const record = await this.pb.collection('AttachmentFile').update(id, {
        fileName: newFileName
      });
      return record;
    } catch (error) {
      console.error('Error updating file name:', error);
      throw error;
    }
  }

  async updateFile(id, file, fileName = null) {
    const formData = new FormData();
    
    formData.append('fileData', file);
    
    if (fileName) {
      formData.append('fileName', fileName);
    }

    formData.append('updated', new Date().toISOString());

    try {
      const record = await this.pb.collection('AttachmentFile').update(id, formData);
      console.log('File updated successfully:', record);
      return record;
    } catch (error) {
      console.error('Error updating file:', error);
      throw error;
    }
  }

}

const pocketbaseService = new PocketBaseService();
export default pocketbaseService;