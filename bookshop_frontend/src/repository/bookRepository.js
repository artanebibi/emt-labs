import axiosInstance from "../axios/axios.js";

const productRepository = {
    findAll: async () => {
        return await axiosInstance.get("/books");
    },
};

export default productRepository;